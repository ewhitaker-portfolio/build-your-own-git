#ifndef CRAFTING_INTERPRETERS_UTILITY_H
#define CRAFTING_INTERPRETERS_UTILITY_H

#ifdef __cplusplus
extern "C" {
#endif

int length(const char* s);
int equals(const char* left, const char* right);

static inline bool isCurrentOrParentDirectory(const char* name) {
    return equals(".", name) == 0 || equals("..", name) == 0;
}

#ifdef __cplusplus
}
#endif

#endif
