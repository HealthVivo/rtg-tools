/*
 * Copyright (c) 2014. Real Time Genomics Limited.
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
package com.rtg.util;

import java.util.Arrays;

import junit.framework.TestCase;

/**
 */
public class QuickSortIntIntProxyTest extends TestCase {
  public void test() {
    final int[] moreints = {50, 42, 10, 45, 20, 20};
    final int[] ints = {0, 1, 2, 3, 4, 5};
    final QuickSortIntIntProxy proxy = new QuickSortIntIntProxy(moreints, ints, false);
    QuickSort.sort(proxy);
    final int[] expected = {0, 3, 1, 4, 5, 2};
    assertTrue("Expected: " + Arrays.toString(expected) + " actual: " + Arrays.toString(ints), Arrays.equals(expected, ints));
    assertTrue(Arrays.equals(new int[]{50, 45, 42, 20, 20, 10}, moreints));

    final QuickSortIntIntProxy proxy2 = new QuickSortIntIntProxy(moreints, ints, true);
    QuickSort.sort(proxy2);
    assertTrue(Arrays.equals(new int[]{10, 20, 20, 42, 45, 50}, moreints));
  }
}
