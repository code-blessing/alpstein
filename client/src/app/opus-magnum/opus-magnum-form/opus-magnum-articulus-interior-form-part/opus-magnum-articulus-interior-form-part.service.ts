import {Injectable} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {ArticulusInteriorWTO} from "@app/wto/articulus-interior.wto";
import {
    OpusMagnumArticulusInteriorFormPartValidationService
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-form-part/opus-magnum-articulus-interior-form-part-validation.service";
import {
    OpusMagnumArticulusInteriorFormPartInitialValueService
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-form-part/opus-magnum-articulus-interior-form-part-initial-value.service";
import {
    OpusMagnumArticulusInteriorFormPartGroup
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-form-part/opus-magnum-articulus-interior-form-part-group";
import {
    OpusMagnumArticulusInteriorFormPartFieldName
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-form-part/opus-magnum-articulus-interior-form-part-field-name";
import {
    registerFormPartValidationTranslations
} from "@app/shared/form-controls/form-part-validation-translations";


@Injectable({providedIn: 'root'})
export class OpusMagnumArticulusInteriorFormPartService {

    constructor(
        private articulusInteriorFormValidationService: OpusMagnumArticulusInteriorFormPartValidationService,
        private articulusInteriorFormInitialValueService: OpusMagnumArticulusInteriorFormPartInitialValueService,
    ) {}

    public createInitialArticulusInteriorForm(): FormGroup<OpusMagnumArticulusInteriorFormPartGroup> {
        const articulusInteriorForm = new FormGroup({
            [OpusMagnumArticulusInteriorFormPartFieldName.scriptumTriviale]: new FormControl<string>(
                this.articulusInteriorFormInitialValueService.scriptumTrivialeInitialValue(),
                {
                    nonNullable: true,
                    validators: this.articulusInteriorFormValidationService.validatorFunctions(OpusMagnumArticulusInteriorFormPartFieldName.scriptumTriviale)
                },
            ),
            [OpusMagnumArticulusInteriorFormPartFieldName.numerusStupidus]: new FormControl<number>(
                this.articulusInteriorFormInitialValueService.numerusStupidusInitialValue(),
                {
                    nonNullable: true,
                    validators: this.articulusInteriorFormValidationService.validatorFunctions(OpusMagnumArticulusInteriorFormPartFieldName.numerusStupidus)
                },
            ),
        });
        return registerFormPartValidationTranslations(articulusInteriorForm, this.articulusInteriorFormValidationService);
    }

    public patchArticulusInteriorForm(form: FormGroup<OpusMagnumArticulusInteriorFormPartGroup>, articulusInterior: ArticulusInteriorWTO): void {
        form.controls[OpusMagnumArticulusInteriorFormPartFieldName.scriptumTriviale].patchValue(articulusInterior.scriptumTriviale);
        form.controls[OpusMagnumArticulusInteriorFormPartFieldName.numerusStupidus].patchValue(articulusInterior.numerusStupidus);
    }

    public createArticulusInteriorWTOFromForm(form: FormGroup<OpusMagnumArticulusInteriorFormPartGroup>): ArticulusInteriorWTO {
        return {
            scriptumTriviale: form.controls[OpusMagnumArticulusInteriorFormPartFieldName.scriptumTriviale].getRawValue(),
            numerusStupidus: form.controls[OpusMagnumArticulusInteriorFormPartFieldName.numerusStupidus].getRawValue(),
        };
    }
}
