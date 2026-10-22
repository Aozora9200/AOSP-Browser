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

import android.content.Context;
import android.net.http.SslCertificate;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.browser.R;

import java.util.Date;

/** Replacement for the hidden SslCertificate#inflateCertificateView(). */
public final class SslCertificateView {
    private SslCertificateView() {
    }

    public static View inflate(Context context, SslCertificate certificate) {
        View view = LayoutInflater.from(context).inflate(R.layout.ssl_certificate, null);

        SslCertificate.DName issuedTo = certificate.getIssuedTo();
        if (issuedTo != null) {
            setText(view, R.id.to_common, issuedTo.getCName());
            setText(view, R.id.to_org, issuedTo.getOName());
            setText(view, R.id.to_org_unit, issuedTo.getUName());
        }

        SslCertificate.DName issuedBy = certificate.getIssuedBy();
        if (issuedBy != null) {
            setText(view, R.id.by_common, issuedBy.getCName());
            setText(view, R.id.by_org, issuedBy.getOName());
            setText(view, R.id.by_org_unit, issuedBy.getUName());
        }

        setText(view, R.id.issued_on, formatDate(context, certificate.getValidNotBeforeDate()));
        setText(view, R.id.expires_on, formatDate(context, certificate.getValidNotAfterDate()));
        return view;
    }

    private static void setText(View parent, int id, String text) {
        ((TextView) parent.findViewById(id)).setText(TextUtils.isEmpty(text) ? "" : text);
    }

    private static String formatDate(Context context, Date date) {
        return date == null ? "" : DateFormat.getDateFormat(context).format(date);
    }
}
