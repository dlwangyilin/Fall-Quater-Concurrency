import concurrent.futures
import glob
import re, collections

class WordCounter:
    def __init__(self):
        self.stopwords = set(open('stop_words').read().split(','))
        self.counts = collections.Counter()

    def countFile(self, fileName):
        words = re.findall('\w{3,}', open(fileName).read().lower())
        self.counts += collections.Counter(w for w in words if w not in self.stopwords)

    def start(self):

        path = '*.txt'
        list_of_args = glob.glob(path)

        with concurrent.futures.ThreadPoolExecutor(max_workers=5) as executor:
            futures = [executor.submit(self.countFile, item) for item in list_of_args]
            for future in concurrent.futures.as_completed(futures):
                future.result()


        for (w, c) in self.counts.most_common(40):
            print(w, '-', c)


if __name__ == "__main__":
    tf = WordCounter()
    tf.start()
