import {ApplicationConfig, InjectionToken, isDevMode} from "@angular/core";
import {provideHttpClient, withInterceptors} from "@angular/common/http";
import {provideTransloco} from "@jsverse/transloco";
import {provideAnimations} from "@angular/platform-browser/animations";
import {PreloadAllModules, provideRouter, withPreloading, withRouterConfig} from "@angular/router";
import {ROUTES} from "@app/app-routing";
import {TranslocoHttpLoader} from "@app/transloco-http-loader";
import {httpErrorInterceptor} from "@app/shared/http-error/http-error.interceptor";
import {
    OpusMagnumSilvaOptionumCampusTextusObligatoriusNamedValidators
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-silva-optionum-form-part/opus-magnum-silva-optionum-form-part-validation.service";
import {
    minimumLengthNamedValidator,
} from "@app/named-validators/field-validators";
// import {
//     ContactContactAgeNamedValidators, ContactContactLastnameNamedValidators
// } from "@app/contact/contact-form/contact-contact-form-part/contact-contact-form-part-validation.service";

export const appConfig: ApplicationConfig = {
    providers: [
        provideHttpClient(withInterceptors([httpErrorInterceptor])),
        provideTransloco({
            config: {
                availableLangs: ['en', 'de'],
                defaultLang: 'en',
                // Remove this option if your application doesn't support changing language in runtime.
                reRenderOnLangChange: true,
                prodMode: !isDevMode(),
            },
            loader: TranslocoHttpLoader,
        }),
        provideRouter(
            ROUTES,
            withPreloading(PreloadAllModules),
            withRouterConfig({
                onSameUrlNavigation: "reload",
            })
        ),
        provideAnimations(),


        // Configure here Validators used in the entity/item forms
        { provide: OpusMagnumSilvaOptionumCampusTextusObligatoriusNamedValidators, useValue:
                [
                    minimumLengthNamedValidator(3),
                ]
        },
        // { provide: ContactContactLastnameNamedValidators, useValue:
        //         [
        //             minimumLengthNamedValidator(6),
        //             maximumLengthNamedValidator(15),
        //         ]
        // },
        // { provide: ContactContactAgeNamedValidators, useValue:
        //         [
        //             minimumNumberNamedValidator(0),
        //             maximumNumberNamedValidator(120),
        //             evenNumberNamedValidator(),
        //         ]
        // },
    ],
};
