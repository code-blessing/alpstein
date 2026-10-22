import {Component, Input, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
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
import {FormArrayEditState} from "@app/shared/form-controls/form-array-edit-state";
import {FieldWrapperComponent} from "@app/shared/form-controls/field-wrapper/field-wrapper.component";
import {
    EntitasRelataMembrumRelatumFormPartValidationService
} from "@app/entitas-relata/entitas-relata-form/entitas-relata-membrum-relatum-form-part/entitas-relata-membrum-relatum-form-part-validation.service";
import {EntitasRelataMembrumRelatumFormPartFieldName,} from "@app/entitas-relata/entitas-relata-form/entitas-relata-membrum-relatum-form-part/entitas-relata-membrum-relatum-form-part-field-name";
import {UUID} from "@app/shared/uuid";
import {TextInputComponent} from "@app/shared/form-controls/text-input/text-input.component";
import {DatepickerInputComponent} from "@app/shared/form-controls/datepicker-input/datepicker-input.component";
import {ValidatorTranslation} from "@app/shared/form-controls/validator-translation";
import {MatTab, MatTabGroup} from "@angular/material/tabs";
import {EntitasRelataMembrumRelatumFormPartGroup} from "@app/entitas-relata/entitas-relata-form/entitas-relata-membrum-relatum-form-part/entitas-relata-membrum-relatum-form-part-group";

import {TranslocoPipe} from "@jsverse/transloco";

@Component({
    selector: 'app-entitas-relata-membrum-relatum-form-part',
    templateUrl: './entitas-relata-membrum-relatum-form-part.component.html',
    styleUrls: ['./entitas-relata-membrum-relatum-form-part.component.scss'],
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
        FieldWrapperComponent,
        MatTabGroup,
        MatTab,
        TextInputComponent,
        TranslocoPipe,
    ]
})
export class EntitasRelataMembrumRelatumFormPartComponent implements OnInit {
    @Input({ required: true }) membrumRelatumForm!: FormGroup<EntitasRelataMembrumRelatumFormPartGroup>;


    protected clavisPrimariaControl!: FormControl<UUID>
    protected clavisPrimariaValidatorNames!: ReadonlyArray<ValidatorTranslation>
    protected descriptioExDistantiControl!: FormControl<string>
    protected descriptioExDistantiValidatorNames!: ReadonlyArray<ValidatorTranslation>

    constructor(private readonly membrumRelatumFormValidationService: EntitasRelataMembrumRelatumFormPartValidationService,) {
    }

    ngOnInit() {
        this.clavisPrimariaControl = this.membrumRelatumForm.controls[EntitasRelataMembrumRelatumFormPartFieldName.clavisPrimaria]
        this.clavisPrimariaValidatorNames = this.membrumRelatumFormValidationService.validatorNames(EntitasRelataMembrumRelatumFormPartFieldName.clavisPrimaria)
        this.descriptioExDistantiControl = this.membrumRelatumForm.controls[EntitasRelataMembrumRelatumFormPartFieldName.descriptioExDistanti]
        this.descriptioExDistantiValidatorNames = this.membrumRelatumFormValidationService.validatorNames(EntitasRelataMembrumRelatumFormPartFieldName.descriptioExDistanti)
    }
}
