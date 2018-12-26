(ns cljfmt-runner.check
  (:require [cljfmt.core :as cljfmt]
            [cljfmt-runner.core :refer :all]
            [cljfmt-runner.diff :as diff]))

(defn -main
  [& args]
  (let [opts (parse-args args)
        dirs (search-dirs opts (config))
        checks (check-all opts dirs)
        failed (filter #(not (:correct? %)) checks)]
    (println (str "Checked " (count checks) " file(s)"))
    (if (< 0 (count failed))
      (do (println failed) (println (str (count failed) " file(s) were incorrectly formatted"))
          (doseq [d (map :diff failed)]
            (println (diff/colorize-diff d)))
          (System/exit 1))
      (println "All files correctly formatted"))))
