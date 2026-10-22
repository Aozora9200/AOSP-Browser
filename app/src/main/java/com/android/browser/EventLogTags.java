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

package com.android.browser;

/**
 * Hand-written replacement for the class the platform build generated from
 * EventLogTags.logtags.
 */
public class EventLogTags {
    private EventLogTags() {
    }

    public static final int BROWSER_BOOKMARK_ADDED = 70103;
    public static final int BROWSER_PAGE_LOADED = 70104;
    public static final int BROWSER_TIMEONPAGE = 70105;
}
