import { Component, OnInit } from '@angular/core';
import { UploadFileService } from '../../service/upload.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-upload-files',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadFilesComponent implements OnInit {

  ngOnInit(): void {
  }
  
  currentFileName: string | null = null;
  currentFile!: File
  message = '';

  constructor(private uploadService : UploadFileService) { }

  selectFile(event : any) {
      this.currentFile = event.target.files[0];
      this.currentFileName = this.currentFile.name 
    } 
  
  upload() {
    if(this.currentFile){
    this.uploadService.upload(this.currentFile).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'NxEFxC.xlsm';
      a.click();
      window.URL.revokeObjectURL(url);
    });
   } 
  }
}

