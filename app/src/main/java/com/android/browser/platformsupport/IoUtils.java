/*
 * Copyright (C) 2026 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.browser.platformsupport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/** Replacements for libcore.io.Streams, libcore.net.http.ResponseUtils and FileUtils. */
public final class IoUtils {
    private IoUtils() {
    }

    public static byte[] readFully(InputStream in) throws IOException {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int count;
            while ((count = in.read(buffer)) != -1) {
                bytes.write(buffer, 0, count);
            }
            return bytes.toByteArray();
        } finally {
            in.close();
        }
    }

    /**
     * Returns the charset named by the content type's "charset" parameter, or
     * ISO-8859-1 if there is none.
     */
    public static Charset responseCharset(String contentType) {
        Charset result = StandardCharsets.ISO_8859_1;
        if (contentType != null) {
            for (String param : contentType.split(";")) {
                String p = param.trim();
                if (p.regionMatches(true, 0, "charset=", 0, "charset=".length())) {
                    String name = p.substring("charset=".length()).replace("\"", "").trim();
                    result = Charset.forName(name);
                }
            }
        }
        return result;
    }

    public static boolean copyFile(File src, File dest) {
        try (InputStream in = new FileInputStream(src);
                OutputStream out = new FileOutputStream(dest)) {
            byte[] buffer = new byte[8192];
            int count;
            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
