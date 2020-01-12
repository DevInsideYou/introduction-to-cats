package devinsideyou
package playground

import cats._
import cats.data._
import cats.implicits._

trait FlexibleErrorHandling[Error, Mechanism[+_, +_]] {
  final type MultipleErrorsOr[+A] = Mechanism[NonEmptyChain[Error], A]

  final implicit protected class FixedApplicativeIdOps[A](private val a: A) {
    final def good(implicit F: Applicative[MultipleErrorsOr]): MultipleErrorsOr[A] =
      F.pure(a)
  }

  final implicit protected class FixedApplicativeErrorIdOps(private val e: Error) {
    final def bad[A](implicit F: ApplicativeError[MultipleErrorsOr, _ >: NonEmptyChain[Error]]): MultipleErrorsOr[A] =
      F.raiseError(e.pure[NonEmptyChain])
  }
}
