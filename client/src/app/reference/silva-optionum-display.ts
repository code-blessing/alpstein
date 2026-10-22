import {UUID} from "@app/shared/uuid";
import {SilvaOptionumWTO} from "@app/wto/silva-optionum.wto";

/**
 * One reference to a SilvaOptionum, ready to be rendered: the display attributes of the
 * resolved SilvaOptionum, already filled with the fallback where the reference could not be
 * resolved.
 *
 * Shared by every place that shows such references, no matter whether the form holds a single
 * reference or a whole list of them.
 */
export interface SilvaOptionumDisplayRow {
    /** Identifies the referenced entry. It is never shown: a bare key says nothing to the user. */
    indexUnicus: UUID
    campusTextusObligatorius: string
    campusNumerorum: string
}

/**
 * The display attributes of a SilvaOptionum: the attributes that identify an instance for a
 * human reader. A reference to a SilvaOptionum is stored as its bare primary key, which tells
 * the user nothing, so every place that shows such a reference resolves it to the whole
 * SilvaOptionumWTO and renders these attributes instead of the primary key alone.
 */
export const SILVA_OPTIONUM_DISPLAY_ATTRIBUTE_NAMES: ReadonlyArray<keyof SilvaOptionumDisplayRow> = [
    'campusTextusObligatorius',
    'campusNumerorum',
];

/** Shown for a display attribute whose reference could not be resolved. */
export const SILVA_OPTIONUM_UNRESOLVED_DISPLAY_VALUE = '—';

/** Flattens the display attributes of a (possibly unresolved) reference into one row. */
export function silvaOptionumDisplayRow(
    indexUnicus: UUID,
    silvaOptionum: SilvaOptionumWTO | undefined,
): SilvaOptionumDisplayRow {
    return {
        indexUnicus: indexUnicus,
        campusTextusObligatorius: (silvaOptionum?.campusTextusObligatorius ?? SILVA_OPTIONUM_UNRESOLVED_DISPLAY_VALUE).toString(),
        campusNumerorum: (silvaOptionum?.campusNumerorum ?? SILVA_OPTIONUM_UNRESOLVED_DISPLAY_VALUE).toString(),
    }
}

/**
 * Joins the display attributes of one reference into a single line, for the places that have
 * room for one line only (the typeahead suggestions and the search field that shows the picked
 * entry).
 */
export function silvaOptionumDisplayRowLabel(displayRow: SilvaOptionumDisplayRow): string {
    return SILVA_OPTIONUM_DISPLAY_ATTRIBUTE_NAMES
        .map(attributeName => displayRow[attributeName])
        .join(' — ');
}

/** The single line label of a whole SilvaOptionum, whose references are all resolved. */
export function silvaOptionumDisplayLabel(silvaOptionum: SilvaOptionumWTO): string {
    return silvaOptionumDisplayRowLabel(
        silvaOptionumDisplayRow(silvaOptionum.indexUnicus, silvaOptionum)
    );
}
