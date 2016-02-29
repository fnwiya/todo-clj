(ns todo-clj.util.validation
  (:require [bouncer.core :as b]))

(defn validate [& args]
  (let [[errors org+errors] (apply b/validate args)]
    (if (nil? errors)
      org+errors
      (throw+ {:type ::validation-error :errors errors}))))

(defmacro with-fallback [fallback & body]
  `(try
     ~@body
     (catch [:type ::validation-error] {:keys [errors#]}
       (~fallback (ex-data e#)))))
