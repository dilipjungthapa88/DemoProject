package com.foo.demo.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import com.foo.demo.exception.DemoException;
import com.foo.demo.exception.StatusCodes;
import com.foo.demo.util.NumberToCharMapper;

public class ComboProcessor {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private AtomicInteger itemCounter = new AtomicInteger(0);
	private ExecutorService THREAD_POOL;

	private int totalItems = 1;
	private int startSearchIndex = 0;
	private int endSearchIndex = 0;

	/*
	 * The phone number is mapped to a nested list where the inner list stores the
	 * alpha-numeric characters for each digit of phone number.
	 */
	private List<List<Character>> phNbrtoMatrix;

	/*
	 * This function is optimized to search through all the alpha-numberic
	 * combinations for a phone number. For larger data sets, it spans upto 4
	 * separate threads running concurrently. Data, that is not required, is skipped
	 * so it does not impacts the performance.
	 */
	public List<String> process(String phoneNumber, int startIndex, int endIndex) throws DemoException {

		logger.debug("Processing phone number " + phoneNumber);

		List<String> response = new ArrayList<String>();
		this.startSearchIndex = startIndex;
		this.endSearchIndex = endIndex;

		System.out.println(startSearchIndex + " : " + endSearchIndex);
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();

		try {

			phNbrtoMatrix = new ArrayList<List<Character>>();
			for (char c : phoneNumber.toCharArray()) {
				phNbrtoMatrix.add(NumberToCharMapper.num2charMap.get(c));
			}

			// Calculate the total combination for the phone number.
			phNbrtoMatrix.forEach(p -> {
				totalItems *= p.size();
			});
			logger.debug("Total possible combinations: " + totalItems);
			
			int tempStartingIndex = -1;
			String tempPrepend = "";

			/*
			 * Find the number with more than one alpha value. 
			 * This is required for multithreading.
			 */
			
			for (int i = 0; i < phNbrtoMatrix.size(); i++) {
				if (phNbrtoMatrix.get(i).size() > 1) {
					tempStartingIndex = i;
					break;
				} else {
					tempPrepend += phNbrtoMatrix.get(i).get(0);
				}
			}

			// This means no combination is possible.
			// eg. (111 111 1111)
			if (tempStartingIndex == -1) {
				response.add(phoneNumber);
				return response;
			}

			// The startingIndex is basically the first digit where the traversal begins.
			// eg. For (123 45 7890), it starts at 'index: 1' since 2 maps to [2, A, B, C] thereby 
			// creating 4 threads if needed.
			
			final Integer startingIndex = new Integer(tempStartingIndex);
			final String prepend = new String(tempPrepend);
			
			if (endIndex >= totalItems)
				endIndex = totalItems - 1;

			// Rather than searching for all the combinations which takes a lot of time,
			// this code looks for the starting ALPHA characters which would contain the start to end indices. 
			int itemsPerChar = totalItems / phNbrtoMatrix.get(startingIndex).size();			
			int startCharIndex = (int) Math.floor(startIndex / itemsPerChar * 1.0f);
			int endACharIndex = (int) Math.floor(endIndex / itemsPerChar * 1.0f);
			itemCounter = new AtomicInteger(startCharIndex * itemsPerChar);

			/*
			 * Each parent character runs on a separate thread 
			 */

			List<Callable<String>> tasks = new ArrayList<Callable<String>>();
			for (int i = startCharIndex; i <= endACharIndex; i++) {
				Integer temp = new Integer(i);
				Callable<String> c = () -> {
					return populateAllCombo(startingIndex + 1,
							prepend + phNbrtoMatrix.get(startingIndex).get(new Integer(temp)).toString());
				};
				tasks.add(c);
			}
			;

			/*
			 * Inititalize thread pool
			 */
			THREAD_POOL = Executors.newFixedThreadPool(tasks.size());
			logger.debug("Invoking " + tasks.size() + " threads");

			/*
			 * The response from all the threads are stored in order.
			 */
			List<Future<String>> futures = THREAD_POOL.invokeAll(tasks, 10000, TimeUnit.MILLISECONDS);

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < futures.size(); i++) {
				sb.append(futures.get(i).get());
			}

			response = Arrays.asList(sb.toString().split(","));
			logger.debug("Found combinations: " + response.size());


		} catch (Exception ex) {

			logger.error("Error processing the data. " + ex.getMessage());
			throw new DemoException("Error processing the data", StatusCodes.INTERNAL_SERVER_ERROR.code,
					StatusCodes.INTERNAL_SERVER_ERROR.message);
		} finally {
			if (THREAD_POOL != null) {
				if (!THREAD_POOL.isTerminated()) {
					THREAD_POOL.shutdown();
				}
			}
		}
		stopwatch.stop();
		logger.debug("Processing phone number completed. Took " + stopwatch.getTotalTimeMillis() + " ms.");
		return response;
	}

	/*
	 * This is a recursive function that traverses through a 2D list in a vertical
	 * fashion. When the end of the tree is reached, it returns the node values for
	 * the path. Also, this function increases the global counter (Atomic for
	 * multi-threading reasons) for each complete path.
	 */
	private String populateAllCombo(final int curProcessingIndex, String nodePath) {

		StringBuilder sb = new StringBuilder();
		if (curProcessingIndex < phNbrtoMatrix.size()) {
			int size = phNbrtoMatrix.get(curProcessingIndex).size();
			for (int i = 0; i < size; i++) {

				if (itemCounter.get() <= endSearchIndex) {
					sb.append(populateAllCombo(curProcessingIndex + 1,
							new String(nodePath) + phNbrtoMatrix.get(curProcessingIndex).get(i)));
				}
			}

		} else {

			if (itemCounter.get() >= startSearchIndex && itemCounter.get() <= endSearchIndex) {
				sb.append(nodePath + ",");
			}
			
			itemCounter.incrementAndGet();
		}
		return sb.toString();
	}

	public int getTotalItems() {
		return totalItems;
	}

}
