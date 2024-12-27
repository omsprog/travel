import {Component} from '@angular/core';
import {DownloadReportsService} from "../services/download-reports.service";

@Component({
  selector: 'dashboard-sidenav',
  templateUrl: './sidenav.component.html',
  styles: []
})
export class SidenavComponent {
  isDataManagementSectionActive = false;
  isSettingsSectionActive = false;

  constructor(private downloadReportsService: DownloadReportsService) { }

  onOpenDataManagementSection() : void {
    this.isDataManagementSectionActive = !this.isDataManagementSectionActive;
  }

  onOpenSettingsSection() : void {
    this.isSettingsSectionActive = !this.isSettingsSectionActive;
  }

  onDownloadSalesSummary() : void {
    this.downloadReportsService.downloadSalesSummary()
  }
}
