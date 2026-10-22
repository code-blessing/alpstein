import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormGroup, ReactiveFormsModule} from '@angular/forms';
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
import {MatDialogModule} from "@angular/material/dialog";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MembrumRelatumWTO} from "@app/wto/membrum-relatum.wto";
import {EntitasRelataMembrumRelatumFormPartService} from "@app/entitas-relata/entitas-relata-form/entitas-relata-membrum-relatum-form-part/entitas-relata-membrum-relatum-form-part.service";
import {EntitasRelataMembrumRelatumFormPartComponent} from "@app/entitas-relata/entitas-relata-form/entitas-relata-membrum-relatum-form-part/entitas-relata-membrum-relatum-form-part.component";
import {
    EntitasRelataMembrumRelatumFormPartGroup
} from "@app/entitas-relata/entitas-relata-form/entitas-relata-membrum-relatum-form-part/entitas-relata-membrum-relatum-form-part-group";
import {
    FormValidationSummaryComponent
} from "@app/shared/form-controls/form-validation-summary/form-validation-summary.component";
import {TranslocoPipe} from "@jsverse/transloco";

@Component({
    selector: 'app-entitas-relata-form',
    templateUrl: './entitas-relata-form.component.html',
    styleUrls: ['./entitas-relata-form.component.scss'],
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
        MatProgressSpinnerModule,
        EntitasRelataMembrumRelatumFormPartComponent,
        FormValidationSummaryComponent,
        TranslocoPipe,
    ]
})
export class EntitasRelataFormComponent implements OnInit {
    @Input() membrumRelatum: MembrumRelatumWTO | null = null;
    @Input() saving = false;
    /** Whether the form offers to discard the edit; only a host that can react to it says yes. */
    @Input() cancellable = false;
    @Output() save = new EventEmitter<MembrumRelatumWTO>();
    @Output() cancel = new EventEmitter<void>();

    membrumRelatumForm: FormGroup<EntitasRelataMembrumRelatumFormPartGroup>;

    constructor(private membrumRelatumFormPartService: EntitasRelataMembrumRelatumFormPartService) {
        this.membrumRelatumForm = membrumRelatumFormPartService.createInitialMembrumRelatumForm();
    }

    ngOnInit(): void {
        if (this.membrumRelatum) {
            this.membrumRelatumFormPartService.patchMembrumRelatumForm(this.membrumRelatumForm, this.membrumRelatum)
        }
    }

    onSubmit(): void {
        if (this.membrumRelatumForm.valid && !this.saving) {
            const updatedMembrumRelatum: MembrumRelatumWTO = this.membrumRelatumFormPartService.createMembrumRelatumWTOFromForm(this.membrumRelatumForm)
            this.save.emit(updatedMembrumRelatum);
        }
    }

    onCancel(): void {
        this.cancel.emit();
    }
}
