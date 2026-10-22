import {AbstractControl, FormArray} from "@angular/forms";
import {FormUtil} from "@app/shared/form-controls/form.util";

/**
 * Encapsulates the "currently edited entry" state of a {@link FormArray} that is presented
 * as a table with an inline edit area.
 *
 * It keeps track of the form group that is currently being edited, allows opening another
 * entry for editing, closing the edit area and deleting an entry from the underlying form
 * array (clearing the edit state if the deleted entry was being edited).
 *
 * It is generic over the control type {@code T} of the form array entries and therefore not
 * tied to a specific form group type.
 */
export class FormArrayEditState<T extends AbstractControl> {
    formGroupUnderEdit: T | undefined = undefined;

    /**
     * @param formArrayProvider returns the form array the entries live in. A provider function is
     *        used (instead of the form array directly) so that the edit state can be created as a
     *        field initializer, before the control is assigned in {@code ngOnInit}.
     */
    constructor(private readonly formArrayProvider: () => FormArray<T>) {
    }

    onEdit(control: T): void {
        this.formGroupUnderEdit = control;
    }

    onDelete(control: T): void {
        if (this.formGroupUnderEdit === control) {
            this.formGroupUnderEdit = undefined;
        }
        FormUtil.removeControl(this.formArrayProvider(), control);
    }

    close(): void {
        this.formGroupUnderEdit = undefined;
    }
}
