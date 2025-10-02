package taller

class ConjuntosDifusos {
  type ConjDifuso = Map[Int, Double] //se uso Map aqui para despues implementarlo
                                    // como un acumulador

  def identificadorN(n: Int, d: Int, e: Int): Double =
    Math.pow(n.toDouble / (n + d).toDouble, e.toDouble) //se uso la biblioteca Math en este caso

  def grande(d: Int, e: Int): ConjDifuso = {
    require(d >= 1 && e > 1)

    val cantInicial = 1
    val cantFinal = 10000

    @annotation.tailrec
    def inicializador(n: Int, acc: ConjDifuso, f: (Int, Int, Int) => Double): ConjDifuso = {
      if (n > cantFinal) acc
      else {
        val valor = f(n, d, e)
        inicializador(n + 1, acc + (n -> valor), f)
      }
    }

    inicializador(cantInicial, Map.empty, identificadorN)
  }
}
