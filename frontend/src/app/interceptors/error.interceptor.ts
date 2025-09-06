import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, from, Observable, throwError, switchMap } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';


@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private snackBar: MatSnackBar) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
     return next.handle(req).pipe(
     catchError((error: HttpErrorResponse) => {
      if (error.error instanceof Blob) {
          return from(error.error.text()).pipe(
          switchMap((text) => {
          const message = this.extractMessage(text);
          this.showError(message);
          return throwError(() => error);
        })
      );
    } else {
          const message =
          error.error?.message || error.error || error.statusText || 'Unbekannter Fehler';
          this.showError(message);
          return throwError(() => error);
    }
      })
    );
  }

  private showError(message: string) {
    this.snackBar.open(`❌ ${message}`, 'Schließen', {
      duration: 5000,
      horizontalPosition: 'center',
      verticalPosition: 'bottom',
      panelClass: ['error-alert']
    });
  }

  private extractMessage(text: string): string {
    try {
      const json = JSON.parse(text);
      return json.message || text;
    } catch {
      return text;
    }
  }
}
