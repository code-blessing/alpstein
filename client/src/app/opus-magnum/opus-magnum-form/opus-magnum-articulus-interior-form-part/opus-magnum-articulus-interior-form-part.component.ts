import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
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
import {FieldWrapperComponent} from "@app/shared/form-controls/field-wrapper/field-wrapper.component";
import {ValidatorTranslation} from "@app/shared/form-controls/validator-translation";
import {TextInputComponent} from "@app/shared/form-controls/text-input/text-input.component";
import {NumberInputComponent} from "@app/shared/form-controls/number-input/number-input.component";
import {
    OpusMagnumArticulusInteriorFormPartFieldName
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-form-part/opus-magnum-articulus-interior-form-part-field-name";
import {
    OpusMagnumArticulusInteriorFormPartGroup
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-form-part/opus-magnum-articulus-interior-form-part-group";
import {
    OpusMagnumArticulusInteriorFormPartValidationService
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-form-part/opus-magnum-articulus-interior-form-part-validation.service";
import {TranslocoPipe} from "@jsverse/transloco";

@Component({
    selector: 'app-opus-magnum-articulus-interior-form-part',
    templateUrl: './opus-magnum-articulus-interior-form-part.component.html',
    styleUrls: ['./opus-magnum-articulus-interior-form-part.component.scss'],
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
        TextInputComponent,
        NumberInputComponent,
        TranslocoPipe,
    ]
})
export class OpusMagnumArticulusInteriorFormPartComponent implements OnInit {
    @Input({required: true}) articulusInteriorForm!: FormGroup<OpusMagnumArticulusInteriorFormPartGroup>;

    constructor(private readonly articulusInteriorFormPartValidationService: OpusMagnumArticulusInteriorFormPartValidationService,) {
    }

    protected articulusInteriorScriptumTrivialeControl!: FormControl<string>
    protected articulusInteriorScriptumTrivialeValidatorNames!: ReadonlyArray<ValidatorTranslation>
    protected articulusInteriorNumerusStupidusControl!: FormControl<number>
    protected articulusInteriorNumerusStupidusValidatorNames!: ReadonlyArray<ValidatorTranslation>

    ngOnInit() {
        this.articulusInteriorScriptumTrivialeControl = this.articulusInteriorForm.controls[OpusMagnumArticulusInteriorFormPartFieldName.scriptumTriviale]
        this.articulusInteriorScriptumTrivialeValidatorNames = this.articulusInteriorFormPartValidationService.validatorNames(OpusMagnumArticulusInteriorFormPartFieldName.scriptumTriviale)
        this.articulusInteriorNumerusStupidusControl = this.articulusInteriorForm.controls[OpusMagnumArticulusInteriorFormPartFieldName.numerusStupidus]
        this.articulusInteriorNumerusStupidusValidatorNames = this.articulusInteriorFormPartValidationService.validatorNames(OpusMagnumArticulusInteriorFormPartFieldName.numerusStupidus)
    }
}
