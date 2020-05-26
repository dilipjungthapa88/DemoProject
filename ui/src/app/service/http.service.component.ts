import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class HttpService{

    username: string = "FOO";
    password: string = "FOOPW";
    role: string = "USER";
    host: string = "http://localhost:8080";
    contextpath: string = "/demo-app";
    resource : string = "/getCombo";

    constructor(private httpClient : HttpClient) {

    }

    getComboForPhoneNumber (phNbr : string, pageNumber: number, itemsPerPage: number) : Observable<any>{
        var url = this.host + this.contextpath + this.resource;
        url = url + "?phNbr="+phNbr+"&pageNumber="+pageNumber+"&itemsPerPage="+itemsPerPage;
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
             'Authorization': 'Basic ' + btoa(this.username + ':' + this.password) 
     });
        return this.httpClient.get(url, {headers} );
    }

}