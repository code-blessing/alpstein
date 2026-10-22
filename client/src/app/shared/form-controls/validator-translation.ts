export interface ValidatorTranslation {
    validatorName: string;
    validatorTranslationKey: string;
    validatorTranslationParams?: Record<string, unknown>;
}

/**
 * Merges the static translation params of a validator with the dynamic payload the
 * validator stored in the error of the control (e.g. { nextEven: 6 }), so messages
 * can reference values that only become known at validation time.
 */
export function mergedValidatorTranslationParams(
    validatorTranslation: ValidatorTranslation | undefined,
    errorPayload: unknown,
): Record<string, unknown> {
    const dynamicParams = (errorPayload && typeof errorPayload === 'object') ? errorPayload : {};
    return {...validatorTranslation?.validatorTranslationParams, ...dynamicParams};
}
