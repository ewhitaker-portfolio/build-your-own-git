#include "Utility.h"
#include "Definitions.h"

INT lengthString(STRING s) {
    STRING p = s;
    INT len = 0;
    while (*(p++) != '\0') {
        ++len;
    }
    return len;
}

INT compareString(STRING left, STRING right) {
    STRING lp = left;
    STRING rp = right;
    INT llen = lengthString(lp);
    INT rlen = lengthString(rp);
    INT lim = MIN(llen, rlen);
    for (INT i = 0; i < lim + 1; ++i) {
        BYTE l = lp[i];
        BYTE r = rp[i];
        if (l != r) {
            return l - r;
        }
    }
    return 0;
}
