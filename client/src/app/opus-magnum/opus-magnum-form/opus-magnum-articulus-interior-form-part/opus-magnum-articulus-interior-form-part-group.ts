import {FormControl} from "@angular/forms";
import {
    OpusMagnumArticulusInteriorFormPartFieldName
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-form-part/opus-magnum-articulus-interior-form-part-field-name";


export interface OpusMagnumArticulusInteriorFormPartGroup {
    [OpusMagnumArticulusInteriorFormPartFieldName.scriptumTriviale]: FormControl<string>,
    [OpusMagnumArticulusInteriorFormPartFieldName.numerusStupidus]: FormControl<number>,
}
