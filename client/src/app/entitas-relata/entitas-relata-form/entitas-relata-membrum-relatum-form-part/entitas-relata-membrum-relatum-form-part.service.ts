
import {Injectable} from '@angular/core';
import {MembrumRelatumWTO} from "@app/wto/membrum-relatum.wto";
import {FormArray, FormControl, FormGroup} from "@angular/forms";
import {
    EntitasRelataMembrumRelatumFormPartValidationService
} from "@app/entitas-relata/entitas-relata-form/entitas-relata-membrum-relatum-form-part/entitas-relata-membrum-relatum-form-part-validation.service";
import {
    EntitasRelataMembrumRelatumFormPartInitialValueService
} from "@app/entitas-relata/entitas-relata-form/entitas-relata-membrum-relatum-form-part/entitas-relata-membrum-relatum-form-part-initial-value.service";
import {EntitasRelataMembrumRelatumFormPartGroup} from "@app/entitas-relata/entitas-relata-form/entitas-relata-membrum-relatum-form-part/entitas-relata-membrum-relatum-form-part-group";
import {EntitasRelataMembrumRelatumFormPartFieldName} from "@app/entitas-relata/entitas-relata-form/entitas-relata-membrum-relatum-form-part/entitas-relata-membrum-relatum-form-part-field-name";
import {
    registerFormPartValidationTranslations
} from "@app/shared/form-controls/form-part-validation-translations";
import {UUID} from "@app/shared/uuid";



@Injectable({providedIn: 'root'})
export class EntitasRelataMembrumRelatumFormPartService {

    constructor(
        private membrumRelatumFormValidationService: EntitasRelataMembrumRelatumFormPartValidationService,
        private membrumRelatumFormInitialValueService: EntitasRelataMembrumRelatumFormPartInitialValueService,
    ) {}

    public createInitialMembrumRelatumForm(): FormGroup<EntitasRelataMembrumRelatumFormPartGroup> {
        const membrumRelatumForm = new FormGroup({
            [EntitasRelataMembrumRelatumFormPartFieldName.clavisPrimaria]: new FormControl<UUID>(
                this.membrumRelatumFormInitialValueService.clavisPrimariaInitialValue(),
                {
                    nonNullable: true,
                    validators: this.membrumRelatumFormValidationService.validatorFunctions(EntitasRelataMembrumRelatumFormPartFieldName.clavisPrimaria)
                },
            ),
            [EntitasRelataMembrumRelatumFormPartFieldName.descriptioExDistanti]: new FormControl<string>(
                this.membrumRelatumFormInitialValueService.descriptioExDistantiInitialValue(),
                {
                    nonNullable: true,
                    validators: this.membrumRelatumFormValidationService.validatorFunctions(EntitasRelataMembrumRelatumFormPartFieldName.descriptioExDistanti)
                },
            ),

        });
        return registerFormPartValidationTranslations(membrumRelatumForm, this.membrumRelatumFormValidationService);
    }



    public patchMembrumRelatumForm(form: FormGroup<EntitasRelataMembrumRelatumFormPartGroup>, membrumRelatum: MembrumRelatumWTO): void {
        this.patchPreparation(form, membrumRelatum);

        form.controls[EntitasRelataMembrumRelatumFormPartFieldName.clavisPrimaria].patchValue(membrumRelatum.clavisPrimaria);
        form.controls[EntitasRelataMembrumRelatumFormPartFieldName.descriptioExDistanti].patchValue(membrumRelatum.descriptioExDistanti);

        this.patchNestedItems(form, membrumRelatum);
    }

    /**
     * patchValue does not create missing FormGroups inside the FormArray.
     * So if your FormArray is empty (or shorter than the incoming data), nothing (or only the first N) gets patched.
     * We need to prefill the FormArray with empty values first
     */
    private patchPreparation(form: FormGroup<EntitasRelataMembrumRelatumFormPartGroup>, membrumRelatum: MembrumRelatumWTO): void {
    }


    private patchNestedItems(form: FormGroup<EntitasRelataMembrumRelatumFormPartGroup>, membrumRelatum: MembrumRelatumWTO): void {

    }

    public createMembrumRelatumWTOFromForm(form: FormGroup<EntitasRelataMembrumRelatumFormPartGroup>): MembrumRelatumWTO {
        return {

            clavisPrimaria: form.controls[EntitasRelataMembrumRelatumFormPartFieldName.clavisPrimaria].getRawValue(),
            descriptioExDistanti: form.controls[EntitasRelataMembrumRelatumFormPartFieldName.descriptioExDistanti].getRawValue(),
        };
    }
}
