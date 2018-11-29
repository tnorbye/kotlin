fun <K> select(vararg x: K): K = x[0]

class Inv<T>(val x: T)

class In<in T> { fun receive(x: T) = 10 }

class Out<out T>(val x: T)