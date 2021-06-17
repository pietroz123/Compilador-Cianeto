package ast;

public class Pairs {

    static class MethodIndex {
        public MethodDec method;
        public Integer index;

        public MethodIndex(MethodDec method, Integer index) {
            this.method = method;
            this.index = index;
        }
    }

}
