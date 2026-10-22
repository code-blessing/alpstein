
import {FormGroup} from "@angular/forms";
import {
    EntitasRelataMembrumRelatumFormPartGroup
} from "@app/entitas-relata/entitas-relata-form/entitas-relata-membrum-relatum-form-part/entitas-relata-membrum-relatum-form-part-group";

export interface EntitasRelataMembrumRelatumTableRow {
    clavisPrimaria: string
    descriptioExDistanti: string
    formGroup: FormGroup<EntitasRelataMembrumRelatumFormPartGroup>
}
