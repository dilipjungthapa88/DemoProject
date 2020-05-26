package com.foo.demo.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumberToCharMapper {
	public static final Map<Character, List<Character>> num2charMap = new HashMap<Character, List<Character>>();
	public static final List<String> EXCLUDE_CHARS_ENDING_IN = Arrays.asList("1","2","3","4","5","6","7","8","9","0");
	static {
		num2charMap.put(' ', Arrays.asList(' '));
		num2charMap.put('1', Arrays.asList('1'));
		num2charMap.put('2', Arrays.asList('2', 'A','B','C'));
		num2charMap.put('3', Arrays.asList('3', 'D','E','F'));
		num2charMap.put('4', Arrays.asList('4', 'G','H','I'));
		num2charMap.put('5', Arrays.asList('5', 'J','K', 'L'));
		num2charMap.put('6', Arrays.asList('6', 'M','N','O'));
		num2charMap.put('7', Arrays.asList('7', 'P','Q','R','S'));
		num2charMap.put('8', Arrays.asList('8', 'T', 'U', 'V'));
		num2charMap.put('9', Arrays.asList('9', 'W','X','Y', 'Z'));
		num2charMap.put('0', Arrays.asList('0', '+'));
	}
	
	

}
