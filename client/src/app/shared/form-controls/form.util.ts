import {AbstractControl, FormArray} from "@angular/forms";

export class FormUtil {
    public static removeControl(formArray: FormArray, control: AbstractControl): void {
        const index = formArray.controls.indexOf(control)
        formArray.removeAt(index, { emitEvent: true })
    }
}
