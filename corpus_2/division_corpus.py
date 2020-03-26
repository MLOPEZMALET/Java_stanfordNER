with open("corpus_histoires.txt", "r") as f:
    list_text = f.readlines()
    text = ",".join(list_text)
    corpus = text.replace(",", "").split("***************************************************\n")
    print(corpus[0])

    for i in range(len(corpus)):
        file = open(f"{i}.txt","w")
        file.write(corpus[i])
        file.close()
