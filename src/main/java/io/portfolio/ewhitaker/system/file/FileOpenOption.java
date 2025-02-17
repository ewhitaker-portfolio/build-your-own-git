package io.portfolio.ewhitaker.system.file;

import io.portfolio.ewhitaker.utility.Flag;

public sealed interface FileOpenOption extends Flag
        permits FileAccessMode, FileCreationOption, FileStatusOption {
}
