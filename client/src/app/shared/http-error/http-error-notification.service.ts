import {inject, Injectable} from "@angular/core";
import {HttpErrorResponse} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";
import {TranslocoService} from "@jsverse/transloco";
import {httpErrorTranslationKey} from "@app/shared/http-error/http-error-message";

const SNACK_BAR_DURATION_MS = 8000;

@Injectable({providedIn: "root"})
export class HttpErrorNotificationService {
    private readonly snackBar = inject(MatSnackBar);
    private readonly transloco = inject(TranslocoService);

    private visibleMessage: string | null = null;

    notify(error: HttpErrorResponse): void {
        const message = this.transloco.translate(httpErrorTranslationKey(error), {status: error.status});

        // Several requests usually fail together (a board loads its rows and all its
        // reference lists at once); showing the same message again would only replace
        // the snack bar the user is already reading.
        if (message === this.visibleMessage) {
            return;
        }
        this.visibleMessage = message;

        const snackBarRef = this.snackBar.open(message, this.transloco.translate("action.close"), {
            duration: SNACK_BAR_DURATION_MS,
            horizontalPosition: "center",
            verticalPosition: "bottom",
            panelClass: "http-error-snack-bar",
        });
        snackBarRef.afterDismissed().subscribe(() => {
            if (this.visibleMessage === message) {
                this.visibleMessage = null;
            }
        });
    }
}
