import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class DownloadReportsService {

  private reportsBaseUrl : string = `http://localhost:8080/travel/reports`;

  constructor(private http: HttpClient) { }

  downloadSalesSummary(): void {
    let salesSummaryUrl = `${this.reportsBaseUrl}/sales-summary`
    this.http
      .get(salesSummaryUrl, {
        responseType: 'blob', // Important for handling binary data
      })
      .subscribe((blob) => {
        const url = window.URL.createObjectURL(blob);
        const anchor = document.createElement('a');
        anchor.href = url;
        anchor.download = 'report.xlsx';
        anchor.click();
        window.URL.revokeObjectURL(url);
      });
  }
}
