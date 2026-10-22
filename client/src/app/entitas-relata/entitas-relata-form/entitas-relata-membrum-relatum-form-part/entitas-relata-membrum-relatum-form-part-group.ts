
import {FormArray, FormControl, FormGroup} from "@angular/forms";
import {EntitasRelataMembrumRelatumFormPartFieldName} from "@app/entitas-relata/entitas-relata-form/entitas-relata-membrum-relatum-form-part/entitas-relata-membrum-relatum-form-part-field-name";
import {UUID} from "@app/shared/uuid";


export interface EntitasRelataMembrumRelatumFormPartGroup {
    [EntitasRelataMembrumRelatumFormPartFieldName.clavisPrimaria]: FormControl<UUID>,
    [EntitasRelataMembrumRelatumFormPartFieldName.descriptioExDistanti]: FormControl<string>,
}
