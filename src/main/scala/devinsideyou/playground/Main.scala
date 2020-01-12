package devinsideyou
package playground

import cats._
import cats.data._
import cats.implicits._

object Main extends App {
  println("─" * 100)

  println(Some(1))
  println(None)

  println(1.some)
  println(none)
  println(none[Int])

  println(Some(null))
  println((null: String).some)

  println(Option(null))

  println(implicitly[Applicative[Option]].pure(1))
  println(Applicative[Option].pure(1))
  println(1.pure[Option])

  println(List(1, 2, 3))
  println(List(1))
  println(1.pure[List])

  // println(NonEmptyList(1, 2, 3)) // does not compile
  println(NonEmptyList.of(1, 2, 3))
  println(NonEmptyList.of(1))
  println(NonEmptyList.one(1))
  println(1.pure[NonEmptyList])

  println(NonEmptyList.fromList(List(1, 2, 3)))
  println(NonEmptyList.fromList(List.empty))

  println(NonEmptyChain(1, 2, 3))
  // println(NonEmptyChain.of(1, 2, 3)) // does not compile
  println(NonEmptyChain(1))
  println(NonEmptyChain.one(1))
  println(1.pure[NonEmptyChain])

  println(NonEmptyChain.fromSeq(List(1, 2, 3)))
  println(NonEmptyChain.fromSeq(List.empty))

  println(Chain(1, 2, 3))
  println(Chain(1))
  println(Chain.one(1))
  println(1.pure[Chain])
  println(Chain.fromSeq(List(1, 2, 3)))

  // println(NonEmptySet(1, 2, 3)) // does not compile
  println(NonEmptySet.of(1, 2, 3))
  println(NonEmptySet.of(1))
  println(NonEmptySet.one(1))
  // println(1.pure[NonEmptySet]) // does not compile
  println(NonEmptySet.fromSet(scala.collection.immutable.SortedSet(1, 2, 3)))

  println(Right(1))
  println(Right[String, Int](1))
  println(1.asRight)
  println(1.asRight[String])

  type EitherStringOr[+A] = Either[String, A]
  println(1.pure[EitherStringOr])
  println(1.pure[Either[String, *]]) // requires the kind projector compiler plugin

  println(Left("error"))
  println(Left[String, Int]("error"))
  println("error".asLeft)
  println("error".asLeft[Int])

  println("error".raiseError[EitherStringOr, String])
  println("error".raiseError[Either[String, *], String]) // requires the kind projector compiler plugin

  println(1.pure[Either[String, *]]) // requires the kind projector compiler plugin
  println("error".raiseError[Either[String, *], String]) // requires the kind projector compiler plugin

  println(1.asRight[String].map(_ + 1))
  println("error".asLeft[Int].leftMap(_.reverse))

  println(1.asRight[String].flatMap(_ => "I don't care that it worked before... it's an error now".asLeft[Int]))
  println("error".asLeft[Int].leftFlatMap(_ => 1337.asRight[String]))
  println("error".asLeft[Int].leftFlatMap(_ => 'M'.asRight[String]))

  println(Right(1).toOption)
  println(Left("error").toOption)

  println(Either.fromOption(Some(1), ifNone = "error"))
  println(Either.fromOption(None, ifNone = "error"))

  println(1.some.toRight(left = "error"))
  println(none.toRight(left = "error"))

  println("error".some.toLeft(right = 1))
  println(none.toLeft(right = 1))

  println(Either.catchOnly[ArithmeticException](1 / 0))
  println(Either.catchOnly[Throwable](1 / 0))
  println(Either.catchNonFatal(1 / 0))

  val a: Either[NonEmptyChain[String], Int] = Left(NonEmptyChain("error1", "error2"))
  val b: EitherNec[String, Int] = Left(NonEmptyChain("error1", "error2"))
  println(a)
  println(b)
  println(Left(NonEmptyChain("error")))
  println(Left[NonEmptyChain[String], Int](NonEmptyChain("error")))
  println(NonEmptyChain("error").asLeft[Int])
  println("error".asLeft[Int].leftMap(NonEmptyChain.one))
  println("error".asLeft[Int].toEitherNec)
  println("error".leftNec[Int])
  println("error".pure[NonEmptyChain].raiseError[EitherNec[String, *], String]) // requires the kind projector compiler plugin
  println(1.asRight[String])
  println(1.rightNec[String])

  println(Validated.Valid(1))
  println(Validated.Invalid("error"))

  import Validated._

  println(Valid(1))
  println(Invalid("error"))

  println(1.valid[String])
  println("error".invalid[Int])

  println(1.validNec[String])
  println("error".invalidNec[Int])

  println("error".pure[NonEmptyChain].raiseError[ValidatedNec[String, *], String]) // requires the kind projector compiler plugin

  println(
    (
      for {
        one <- 1.valid[String].toEither
        two <- 2.asRight[String]
      } yield one + two
    ).toValidated
  )

  println(
    1.valid[String]
      .toEither
      .flatMap { one =>
        2.asRight[String].map { two =>
          one + two
        }
      }
      .toValidated
  )

  println(
    1.valid[String].withEither { either =>
      either.flatMap { one =>
        2.asRight[String].map { two =>
          one + two
        }
      }
    }
  )

  println(
    1.valid[String].andThen { one =>
      2.valid[String].map { two =>
        one + two
      }
    }
  )

  println(
    1.asRight[String].flatMap { one =>
      2.asRight[String].map { two =>
        one + two
      }
    }
  )

  println(
    Apply[Option].map3(Some(1), Some(2), Some(3)) { (one, two, three) =>
      one + two + three
    }
  )

  println(
    (
      1.some,
      2.some,
      3.some
    ).mapN { (one, two, three) =>
      one + two + three
    }
  )

  println(
    for {
      one <- 1.some
      two <- 2.some
      three <- 3.some
    } yield one + two + three
  )

  println(
    for {
      one <- "error 1".leftNec[Int]
      two <- "error 2".leftNec[Int]
      three <- "error 3".leftNec[Int]
    } yield one + two + three
  )

  println(
    (
      "error 1".invalidNec[Int],
      "error 2".invalidNec[Int],
      "error 3".invalidNec[Int]
    ).mapN { (one, two, three) =>
      one + two + three
    }
  )

  println(
    (
      "error 1".leftNec[Int],
      "error 2".leftNec[Int],
      "error 3".leftNec[Int]
    ).mapN { (one, two, three) =>
      one + two + three
    }
  )

  case object Whatever

  implicit val horribleButCompiles: Semigroup[Whatever.type] =
    (w1, _) => w1

  println(
    (
      Whatever.invalid /* Nec */ [Int],
      Whatever.invalid /* Nec */ [Int],
      Whatever.invalid /* Nec */ [Int]
    ).mapN { (one, two, three) =>
      one + two + three
    }
  )

  println(
    (
      List('a', 'b', 'c'),
      List(1, 2, 3)
    ).mapN { (c, n) =>
      c -> n
    }
  )

  println {
    for {
      c <- List('a', 'b', 'c')
      n <- List(1, 2, 3)
    } yield c -> n
  }

  println(
    (
      List('a', 'b', 'c'),
      List(1, 2, 3)
    ).mapN(Tuple2[Char, Int])
  )

  println(
    (
      List('a', 'b', 'c'),
      List(1, 2, 3)
    ).mapN(Tuple2.apply)
  )

  println(
    (
      List('a', 'b', 'c'),
      List(1, 2, 3)
    ).tupled
  )

  println(
    (
      List('a', 'b', 'c'),
      List(1, 2, 3)
    ).parMapN { (c, n) =>
      c -> n
    }
  )

  println(
    (
      "error 1".leftNec[Int],
      "error 2".leftNec[Int],
      "error 3".leftNec[Int]
    ).mapN { (one, two, three) =>
      one + two + three
    }
  )

  println(
    (
      "error 1".leftNec[Int],
      "error 2".leftNec[Int],
      "error 3".leftNec[Int]
    ).parMapN { (one, two, three) =>
      one + two + three
    }
  )

  final case class Report(days: Int, earned: Int)

  println(
    (
      Report(10, 100).validNec[String],
      Report(20, 200).validNec[String]
    ).mapN { (firstReport, secondReport) =>
      Report(
        days = firstReport.days + secondReport.days,
        earned = firstReport.earned + secondReport.earned
      )
    }
  )

  import scala.concurrent._
  import scala.concurrent.duration._
  implicit val ec = scala.concurrent.ExecutionContext.global

  def awaitResult[A](future: Future[A]): A =
    Await.result(future, 5.seconds)

  println(
    awaitResult {
      (
        Future("error 1".invalidNec[Report]),
        Future("error 2".invalidNec[Report])
      ).mapN { (firstValidated, secondValidated) =>
        (
          firstValidated,
          secondValidated
        ).mapN { (firstReport, secondReport) =>
          Report(
            days = firstReport.days + secondReport.days,
            earned = firstReport.earned + secondReport.earned
          )
        }
      }
    }
  )

  println(
    awaitResult {
      (
        Future("error 1".invalidNec[Report]).nested,
        Future("error 2".invalidNec[Report]).nested
      ).mapN { (firstReport, secondReport) =>
        Report(
          days = firstReport.days + secondReport.days,
          earned = firstReport.earned + secondReport.earned
        )
      }.value
    }
  )

  println(
    awaitResult {
      (
        for {
          firstReport <- EitherT(Future("error 1".leftNec[Report]))
          secondReport <- EitherT(Future("error 2".leftNec[Report]))
        } yield Report(
          days = firstReport.days + secondReport.days,
          earned = firstReport.earned + secondReport.earned
        )
      ).value
    }
  )

  println(
    awaitResult {
      (
        for {
          firstReport <- Future("error 1".leftNec[Report])
          secondReport <- Future("error 2".leftNec[Report])
        } yield (firstReport, secondReport).mapN { (firstReport, secondReport) =>
          Report(
            days = firstReport.days + secondReport.days,
            earned = firstReport.earned + secondReport.earned
          )
        }
      )
    }
  )

  println(
    awaitResult {
      (
        for {
          firstReport <- Future("error 1".invalidNec[Report])
          secondReport <- Future("error 2".invalidNec[Report])
        } yield (firstReport, secondReport).mapN { (firstReport, secondReport) =>
          Report(
            days = firstReport.days + secondReport.days,
            earned = firstReport.earned + secondReport.earned
          )
        }
      )
    }
  )

  println(
    (
      10.validNec[String],
      20.validNec[String],
      30.validNec[String]
    ).mapN { (_, _, last) =>
      last
    }
  )

  println(
    (
      10.validNec[String] *> // format: OFF
      20.validNec[String] *>
      30.validNec[String]    // format: ON
    ).map { last =>
      last
    }
  )

  println(
    (
      10.validNec[String],
      20.validNec[String],
      30.validNec[String]
    ).mapN { (first, _, _) =>
      first
    }
  )

  println(
    (
      10.validNec[String] <* // format: OFF
      20.validNec[String] <*
      30.validNec[String]    // format: ON
    ).map { first =>
      first
    }
  )

  println("─" * 100)
}
