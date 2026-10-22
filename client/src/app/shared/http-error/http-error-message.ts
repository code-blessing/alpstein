import {HttpErrorResponse} from "@angular/common/http";

export function httpErrorTranslationKey(error: HttpErrorResponse): string {
    if (error.status === 0) {
        return "error.http.unreachable";
    }
    switch (error.status) {
        case 400:
            return "error.http.badRequest";
        case 401:
            return "error.http.unauthorized";
        case 403:
            return "error.http.forbidden";
        case 404:
            return "error.http.notFound";
        case 409:
            return "error.http.conflict";
        case 422:
            return "error.http.unprocessable";
        default:
            return error.status >= 500 ? "error.http.serverError" : "error.http.unknown";
    }
}
