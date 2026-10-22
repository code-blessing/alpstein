import {HttpErrorResponse, HttpInterceptorFn} from "@angular/common/http";
import {inject} from "@angular/core";
import {catchError, throwError} from "rxjs";
import {HttpErrorNotificationService} from "@app/shared/http-error/http-error-notification.service";

export const httpErrorInterceptor: HttpInterceptorFn = (request, next) => {
    const notificationService = inject(HttpErrorNotificationService);

    return next(request).pipe(
        catchError((error: unknown) => {
            if (error instanceof HttpErrorResponse) {
                notificationService.notify(error);
            }
            return throwError(() => error);
        })
    );
};
