import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpHeaders, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {

  private baseUrl = 'http://localhost:8080/automation/upload';

  constructor(private http: HttpClient) { }

  upload(file: File): Observable<Blob> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    return this.http.post(this.baseUrl,formData, {
      responseType : 'blob'
      
    });
  }
}
