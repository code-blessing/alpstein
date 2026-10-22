import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import {ReactiveFormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatTableModule} from "@angular/material/table";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {MembrumRelatumWTO} from "@app/wto/membrum-relatum.wto";
import {JsonPipe} from "@angular/common";
import {TranslocoPipe} from "@jsverse/transloco";

@Component({
    selector: 'app-entitas-relata-confirm-delete-dialog',
    templateUrl: './entitas-relata-confirm-delete-dialog.component.html',
    styleUrls: ['./entitas-relata-confirm-delete-dialog.component.scss'],
    imports: [
        ReactiveFormsModule,
        MatButtonModule,
        MatToolbarModule,
        MatTableModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        MatExpansionModule,
        MatSidenavModule,
        MatListModule,
        MatDialogModule,
        JsonPipe,
        TranslocoPipe,
    ]
})
export class EntitasRelataConfirmDeleteDialogComponent {
    constructor(
        public dialogRef: MatDialogRef<EntitasRelataConfirmDeleteDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: {
            entity: MembrumRelatumWTO,
        }
    ) {
    }

    onCancel(): void {
        this.dialogRef.close(false);
    }

    onConfirm(): void {
        this.dialogRef.close(true);
    }
} 
