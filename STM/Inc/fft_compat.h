#define float32_t float

void arm_cmplx_mag_squared_f32(
  float32_t * pSrc,
  float32_t * pDst,
  uint32_t numSamples)
{
  float32_t real, imag;                          /* Temporary variables to store real and imaginary values */
  uint32_t blkCnt;                               /* loop counter */

#ifndef ARM_MATH_CM0_FAMILY
  float32_t real1, real2, real3, real4;          /* Temporary variables to hold real values */
  float32_t imag1, imag2, imag3, imag4;          /* Temporary variables to hold imaginary values */
  float32_t mul1, mul2, mul3, mul4;              /* Temporary variables */
  float32_t mul5, mul6, mul7, mul8;              /* Temporary variables */
  float32_t out1, out2, out3, out4;              /* Temporary variables to hold output values */

  /*loop Unrolling */
  blkCnt = numSamples >> 2u;

  /* First part of the processing with loop unrolling.  Compute 4 outputs at a time.
   ** a second loop below computes the remaining 1 to 3 samples. */
  while(blkCnt > 0u)
  {
    /* C[0] = (A[0] * A[0] + A[1] * A[1]) */
    /* read real input sample from source buffer */
    real1 = pSrc[0];
    /* read imaginary input sample from source buffer */
    imag1 = pSrc[1];

    /* calculate power of real value */
    mul1 = real1 * real1;

    /* read real input sample from source buffer */
    real2 = pSrc[2];

    /* calculate power of imaginary value */
    mul2 = imag1 * imag1;

    /* read imaginary input sample from source buffer */
    imag2 = pSrc[3];

    /* calculate power of real value */
    mul3 = real2 * real2;

    /* read real input sample from source buffer */
    real3 = pSrc[4];

    /* calculate power of imaginary value */
    mul4 = imag2 * imag2;

    /* read imaginary input sample from source buffer */
    imag3 = pSrc[5];

    /* calculate power of real value */
    mul5 = real3 * real3;
    /* calculate power of imaginary value */
    mul6 = imag3 * imag3;

    /* read real input sample from source buffer */
    real4 = pSrc[6];

    /* accumulate real and imaginary powers */
    out1 = mul1 + mul2;

    /* read imaginary input sample from source buffer */
    imag4 = pSrc[7];

    /* accumulate real and imaginary powers */
    out2 = mul3 + mul4;

    /* calculate power of real value */
    mul7 = real4 * real4;
    /* calculate power of imaginary value */
    mul8 = imag4 * imag4;

    /* store output to destination */
    pDst[0] = out1;

    /* accumulate real and imaginary powers */
    out3 = mul5 + mul6;

    /* store output to destination */
    pDst[1] = out2;

    /* accumulate real and imaginary powers */
    out4 = mul7 + mul8;

    /* store output to destination */
    pDst[2] = out3;

    /* increment destination pointer by 8 to process next samples */
    pSrc += 8u;

    /* store output to destination */
    pDst[3] = out4;

    /* increment destination pointer by 4 to process next samples */
    pDst += 4u;

    /* Decrement the loop counter */
    blkCnt--;
  }

  /* If the numSamples is not a multiple of 4, compute any remaining output samples here.
   ** No loop unrolling is used. */
  blkCnt = numSamples % 0x4u;

#else

  /* Run the below code for Cortex-M0 */

  blkCnt = numSamples;

#endif /* #ifndef ARM_MATH_CM0_FAMILY */

  while(blkCnt > 0u)
  {
    /* C[0] = (A[0] * A[0] + A[1] * A[1]) */
    real = *pSrc++;
    imag = *pSrc++;

    /* out = (real * real) + (imag * imag) */
    /* store the result in the destination buffer. */
    *pDst++ = (real * real) + (imag * imag);

    /* Decrement the loop counter */
    blkCnt--;
  }
}

void arm_max_f32(
  float32_t * pSrc,
  uint32_t blockSize,
  float32_t * pResult,
  uint32_t * pIndex)
{
#ifndef ARM_MATH_CM0_FAMILY

  /* Run the below code for Cortex-M4 and Cortex-M3 */
  float32_t maxVal1, maxVal2, out;               /* Temporary variables to store the output value. */
  uint32_t blkCnt, outIndex, count;              /* loop counter */

  /* Initialise the count value. */
  count = 0u;
  /* Initialise the index value to zero. */
  outIndex = 0u;
  /* Load first input value that act as reference value for comparision */
  out = *pSrc++;

  /* Loop unrolling */
  blkCnt = (blockSize - 1u) >> 2u;

  /* Run the below code for Cortex-M4 and Cortex-M3 */
  while(blkCnt > 0u)
  {
    /* Initialize maxVal to the next consecutive values one by one */
    maxVal1 = *pSrc++;

    maxVal2 = *pSrc++;

    /* compare for the maximum value */
    if(out < maxVal1)
    {
      /* Update the maximum value and its index */
      out = maxVal1;
      outIndex = count + 1u;
    }

    maxVal1 = *pSrc++;

    /* compare for the maximum value */
    if(out < maxVal2)
    {
      /* Update the maximum value and its index */
      out = maxVal2;
      outIndex = count + 2u;
    }

    maxVal2 = *pSrc++;

    /* compare for the maximum value */
    if(out < maxVal1)
    {
      /* Update the maximum value and its index */
      out = maxVal1;
      outIndex = count + 3u;
    }

    /* compare for the maximum value */
    if(out < maxVal2)
    {
      /* Update the maximum value and its index */
      out = maxVal2;
      outIndex = count + 4u;
    }

    count += 4u;

    /* Decrement the loop counter */
    blkCnt--;
  }

  /* if (blockSize - 1u) is not multiple of 4 */
  blkCnt = (blockSize - 1u) % 4u;

#else

  /* Run the below code for Cortex-M0 */
  float32_t maxVal1, out;                        /* Temporary variables to store the output value. */
  uint32_t blkCnt, outIndex;                     /* loop counter */

  /* Initialise the index value to zero. */
  outIndex = 0u;
  /* Load first input value that act as reference value for comparision */
  out = *pSrc++;

  blkCnt = (blockSize - 1u);

#endif /* #ifndef ARM_MATH_CM0_FAMILY */

  while(blkCnt > 0u)
  {
    /* Initialize maxVal to the next consecutive values one by one */
    maxVal1 = *pSrc++;

    /* compare for the maximum value */
    if(out < maxVal1)
    {
      /* Update the maximum value and it's index */
      out = maxVal1;
      outIndex = blockSize - blkCnt;
    }


    /* Decrement the loop counter */
    blkCnt--;

  }

  /* Store the maximum value and it's index into destination pointers */
  *pResult = out;
  *pIndex = outIndex;
}
