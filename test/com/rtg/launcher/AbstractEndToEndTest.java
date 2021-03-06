/*
 * Copyright (c) 2017. Real Time Genomics Limited.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the
 *    distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.rtg.launcher;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import com.rtg.tabix.UnindexableDataException;
import com.rtg.util.TestUtils;
import com.rtg.util.io.FileUtils;
import com.rtg.vcf.VcfUtils;

/**
 *
 */
public abstract class AbstractEndToEndTest extends AbstractCliTest {

  protected void endToEnd(String id, String[] filesToCheck, boolean expectWarn, String... args) throws IOException, UnindexableDataException {
    endToEnd(id, id, filesToCheck, expectWarn, null, args);
  }

  protected void endToEnd(String harnessId, String resultsId, String[] filesToCheck, boolean expectWarn, String... args) throws IOException, UnindexableDataException {
    endToEnd(harnessId, resultsId, filesToCheck, expectWarn, null, args);
  }

  protected void endToEnd(String id, String[] filesToCheck, boolean expectWarn, Consumer<File> extracheck, String... args) throws IOException, UnindexableDataException {
    endToEnd(id, id, filesToCheck, expectWarn, extracheck, args);
  }

  /**
   * Run a command line test with the given harness and results id, and check the results.
   * @param harnessId specifier for which input files to use
   * @param resultsId specifier for which input files to use
   * @param filesToCheck list of output file names to check
   * @param expectWarn if true, check the results of stderr
   * @param extracheck if not null, run custom checks in the given output directory
   * @param args additional command line arguments
   * @throws IOException if bad things happen
   * @throws UnindexableDataException if other bad things happen
   */
  protected abstract void endToEnd(String harnessId, String resultsId, String[] filesToCheck, boolean expectWarn, Consumer<File> extracheck, String... args) throws IOException, UnindexableDataException;

  // For calling by the endToEnd implementation
  protected void checkResults(String id, String[] filesToCheck, boolean expectWarn, Consumer<File> extracheck, File output, MainResult res) throws IOException {
    assertEquals(res.err(), 0, res.rc());
    if (expectWarn) {
      mNano.check(id + "_err.txt", res.err());
    }

    for (String fileName : filesToCheck) {
      final File file = new File(output, fileName);
      final String content = FileUtils.fileToString(file);
      mNano.check(id + "_out_" + fileName, VcfUtils.isVcfExtension(file) ? TestUtils.sanitizeVcfHeader(content) : TestUtils.sanitizeTsvHeader(content));
    }

    if (extracheck != null) {
      extracheck.accept(output);
    }
  }
}
