import {Injectable} from '@angular/core';


@Injectable({providedIn: 'root'})
export class OpusMagnumArticulusInteriorFormPartInitialValueService {

    scriptumTrivialeInitialValue(): string {
        return ''
    }

    numerusStupidusInitialValue(): number {
        return 2025
    }
}
