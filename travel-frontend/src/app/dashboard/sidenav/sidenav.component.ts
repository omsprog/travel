import {Component} from '@angular/core';
import {DownloadReportsService} from "../services/download-reports.service";

@Component({
  selector: 'dashboard-sidenav',
  templateUrl: './sidenav.component.html',
  styles: []
})
export class SidenavComponent {
  isReportsSectionActive = false;

  constructor(private downloadReportsService: DownloadReportsService) { }

  onClickOpenReports() : void {
    this.isReportsSectionActive = !this.isReportsSectionActive;
  }

  onDownloadSalesSummary() : void {
    this.downloadReportsService.downloadSalesSummary()
  }
}
