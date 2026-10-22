import {NamedValidator} from "@app/shared/form-controls/named-validator";

export interface FormFieldsValidation {
    [index: string]: ReadonlyArray<NamedValidator>
}
