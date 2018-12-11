package authoringUtils.frontendUtils;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Try Class encapsulates the result of an operation that may or may not succeed.
 * It provides some convenience methods to operate on it.
 * <p>
 * https://www.scala-lang.org/api/2.12.1/scala/util/Try.html
 */

public abstract class Try<T> {
    public static <U> Try<U> apply(ThrowingSupplier<U> op) {
        try {
            return Try.success(op.get());
        } catch (Throwable e) {
            return Try.failure(e);
        }
    }

    public static <U> Try<U> success(U val) {
        return new Success<>(val);
    }

    public static <U> Try<U> failure(Throwable t) {
        return new Failure<>(t);
    }

    public static Supplier<Throwable> supplyThrow(Throwable t) {
        return () -> t;
    }

    abstract public T get() throws Throwable;

    abstract public boolean isSuccess();

    abstract public boolean isFailure();

    abstract public <U> Try<U> flatMap(Function<T, Try<U>> f);

    abstract public <U> Try<U> map(Function<T, U> f);

    abstract public void forEach(Consumer<T> op);

    abstract public T get(T backup);

    private static class Success<R> extends Try<R> {
        private R val;

        public Success(R val) {
            this.val = val;
        }

        @Override
        public R get() {
            return val;
        }

        @Override
        public boolean isFailure() {
            return false;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public <U> Try<U> flatMap(Function<R, Try<U>> f) {
            return f.apply(val);
        }

        @Override
        public <U> Try<U> map(Function<R, U> f) {
            return new Success<>(f.apply(val));
        }

        @Override
        public void forEach(Consumer<R> op) {
            op.accept(val);
        }

        @Override
        public R get(R backup) {
            return val;
        }
    }

    private static class Failure<R> extends Try<R> {
        private Throwable t;

        public Failure(Throwable t) {
            this.t = t;
        }

        @Override
        public R get() throws Throwable {
            throw t;
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public boolean isFailure() {
            return true;
        }

        @Override
        public <U> Try<U> flatMap(Function<R, Try<U>> f) {
            return Try.failure(t);
        }

        @Override
        public <U> Try<U> map(Function<R, U> f) {
            return Try.failure(t);
        }

        @Override
        public void forEach(Consumer<R> op) {
        }

        @Override
        public R get(R backup) {
            return backup;
        }
    }

}
