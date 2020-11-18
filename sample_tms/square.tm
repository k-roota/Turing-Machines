start 0 accept 0 S
start 1 goputright1 3 R
start 2 goputright2 3 R

goputright1 1 goputright1 1 R
goputright1 2 goputright2 1 R
goputright2 1 goputright1 2 R
goputright2 2 goputright2 2 R

goputright1 0 putright 1 R
goputright2 0 putright 2 R

putright 0 goswapleft 3 L

goswapleft 1 goswapleft1 1 L
goswapleft 2 goswapleft2 2 L

goswapleft1 1 goswapleft1 1 L
goswapleft1 2 goswapleft2 2 L
goswapleft2 1 goswapleft1 1 L
goswapleft2 2 goswapleft2 2 L

goswapleft1 3 swapleft 1 R
goswapleft2 3 swapleft 2 R

swapleft 1 goswapright 3 R
swapleft 2 goswapright 3 R

goswapright 1 goswapright1 1 R
goswapright 2 goswapright2 2 R
goswapright 3 reject 3 S

goswapright1 1 goswapright1 1 R
goswapright1 2 goswapright2 2 R
goswapright2 1 goswapright1 1 R
goswapright2 2 goswapright2 2 R

goswapright1 3 swapright 1 L
goswapright2 3 swapright 2 L

swapright 1 goswapleft 3 L
swapright 2 goswapleft 3 L

goswapleft 3 gocheckright 3 R

gocheckright 0 checkright 0 L
gocheckright 1 gocheckright 1 R
gocheckright 2 gocheckright 2 R
gocheckright 3 gocheckright 3 R

checkright 1 gocheckleft1 0 L
checkright 2 gocheckleft2 0 L
checkright 3 accept 3 S

gocheckleft1 1 gocheckleft1 1 L
gocheckleft1 2 gocheckleft1 2 L
gocheckleft1 3 checkleft1 3 L
gocheckleft2 1 gocheckleft2 1 L
gocheckleft2 2 gocheckleft2 2 L
gocheckleft2 3 checkleft2 3 L

checkleft1 1 gocheckright 3 R
checkleft1 2 reject 2 S
checkleft1 3 checkleft1 3 L
checkleft2 1 reject 1 S
checkleft2 2 gocheckright 3 R
checkleft2 3 checkleft2 3 L
