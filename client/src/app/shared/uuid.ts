/**
 * A universally unique identifier in its canonical textual form
 * (`xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx`).
 *
 * On the wire and in the browser a UUID is transported and edited as plain text, so this
 * is an alias of `string`. It exists to make the intent of an attribute explicit and to
 * mirror the `java.util.UUID` typing used on the backend.
 */
export type UUID = string;

/** The nil UUID, used as the initial value of a UUID form field that has no value yet. */
export const NIL_UUID: UUID = '00000000-0000-0000-0000-000000000000';
