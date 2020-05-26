import { HttpService } from '../service/http.service.component';
import { DemoResponse } from '../models/demo.response.component';
import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

    curPageNum: number;
    itemsPerPage: number = 100;
    phoneNumber: string;
    response: DemoResponse;
    pages: number[] = [];
    basePageNum: number;
    from: number;
    to: number;
    isLoading: boolean = false;
    phoneRegex = new RegExp("\^[\\d]{3}\\s[\\d]{3}\\s[\\d]{4}$|\^[\\d]{3}\\s[\\d]{4}$|\^[\\d]{7}$|\^[\\d]{10}$");


    constructor(private httpService: HttpService) {
    }

    ngOnInit() {
        this.setDefaults();
    }

    setDefaults() {
        this.curPageNum = 1;
        this.basePageNum = 0;
        this.response = null;
    }

    onItemChange(value: number) {
        this.setDefaults();
        this.itemsPerPage = value;
        if (this.phoneNumber) {
            this.getCombo();
        }
    }

    setPages() {
        this.pages = [];

        if (this.response) {
            for (var i = 0; i < 10; i++) {

                if (this.basePageNum + i <= this.response.totalPages) {
                    if (this.basePageNum + i > 0) {
                        this.pages.push(this.basePageNum + i);
                    }

                }

            }
        }

    }

    previousPages() {
        if (this.basePageNum > 0) {
            this.basePageNum = this.basePageNum - 10;
            this.setPages();
        }
    }

    nextPages() {
        this.basePageNum = this.basePageNum + 10;
        this.setPages();
    }

    goTopage(pageNum: number) {

        if (pageNum > 0 && pageNum <= this.response.totalPages) {
            this.curPageNum = pageNum;
            this.getCombo();
        }

    }

    postDataFetch(data: DemoResponse) {
        this.response = data;
        this.from = (this.curPageNum - 1) * this.itemsPerPage + 1;
        this.to = this.from + this.response.itemsInCurPage - 1;
        this.setPages();

    }

    validate(phoneNumber) {
        return this.phoneRegex.test(phoneNumber);

    }

    search(phoneNumber) {

        if (!this.validate(phoneNumber)) {
            alert("Please enter valid 7 or 10 digit number (1234567890 or 123 456 7890) ");
            return;
        }

        if (!this.isLoading) {
            this.setDefaults();
            this.phoneNumber = phoneNumber;
            this.getCombo();
        }

    }

    getCombo() {

        this.isLoading = true;
        this.httpService.getComboForPhoneNumber(this.phoneNumber, this.curPageNum, this.itemsPerPage)
            .subscribe((data: DemoResponse) => {
                this.postDataFetch(data);
                this.isLoading = false;
            }, (err) => {
                this.response = new DemoResponse();
                this.response.message = "Error getting details. [" + err.status + "]"
                this.isLoading = false;
            });
    }

}