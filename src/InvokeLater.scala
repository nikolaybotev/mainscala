object InvokeLater extends App {
  
  class Promise[+T](value: T) {
    def when[V](c: T => V): Promise[V] = {
      return new Promise[V](c(value))
    }
  }

  class FunctionProxy0[+R](f: Function0[R]) {
    def ! : Promise[R] = { print("invoked later0: "); new Promise(f.apply) }
  }
  implicit def funLater0[R](f: Function0[R]): FunctionProxy0[R] = new FunctionProxy0(f)

  class FunctionProxy1[-T1, +R](f: Function1[T1, R]) {
    def ! (v1: T1) : Promise[R] = { print("invoked later1: "); new Promise(f.apply(v1)) }
  }
  implicit def funLater1[T1, R](f: Function1[T1, R]): FunctionProxy1[T1, R] = new FunctionProxy1(f)
  
  class FunctionProxy2[-T1, -T2, +R](f: Function2[T1, T2, R]) {
    def ! (v1: T1, v2: T2) : Promise[R] = { print("invoked later2: "); new Promise(f.apply(v1, v2)) }
  }
  implicit def funLater2[T1, T2, R](f: Function2[T1, T2, R]): FunctionProxy2[T1, T2, R] = new FunctionProxy2(f)
  
  class FunctionProxy3[-T1, -T2, -T3, +R](f: Function3[T1, T2, T3, R]) {
    def ! (v1: T1, v2: T2, v3: T3) : Promise[R] = { print("invoked later3: "); new Promise(f(v1, v2, v3)) }
  }
  implicit def funLater3[T1, T2, T3, R](f: Function3[T1, T2, T3, R]): FunctionProxy3[T1, T2, T3, R] = new FunctionProxy3(f)
  
  class Test {
    def x0() = { println("hello0") }
    def x1(v1: Int) = { println("hello1 " + v1) }
    def x2(v1: Int, v2: Int) = { println("hello2 " + v1 + ", " + v2) }
    def x3(v1: Int, v2: Int, v3: Int) = { println("hello3 " + v1 + ", " + v2 + ", " + v3) }
  }
  
  val a = new Test
  
  a.x0 _!;
  a.x1 _! (10)
  a.x2 _! (10, 20)
  a.x3 _! (10, 20, 30) when { x => x }
}

