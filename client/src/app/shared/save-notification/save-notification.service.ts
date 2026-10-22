import {inject, Injectable} from "@angular/core";
import {MatSnackBar} from "@angular/material/snack-bar";
import {TranslocoService} from "@jsverse/transloco";

const SNACK_BAR_DURATION_MS = 3000;

@Injectable({providedIn: "root"})
export class SaveNotificationService {
    private readonly snackBar = inject(MatSnackBar);
    private readonly transloco = inject(TranslocoService);

    notifySaved(): void {
        this.snackBar.open(this.transloco.translate("text.saved"), this.transloco.translate("action.close"), {
            duration: SNACK_BAR_DURATION_MS,
            horizontalPosition: "center",
            verticalPosition: "bottom",
        });
    }
}
