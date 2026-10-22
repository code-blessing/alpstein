
import {Injectable} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {UUID} from "@app/shared/uuid";


@Injectable({providedIn: 'root'})
export class EntitasRelataMembrumRelatumFormPartInitialValueService {
    clavisPrimariaInitialValue(): UUID {
        return '00000000-0000-0000-0000-000000000000'
    }
    descriptioExDistantiInitialValue(): string {
        return ''
    }
}
