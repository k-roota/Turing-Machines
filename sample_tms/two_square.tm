start 0 0 accept 0 0 S S
start 1 0 goputright1 3 0 R R
start 2 0 goputright2 3 0 R R

goputright1 1 0 goputright1 1 0 R R
goputright1 2 0 goputright2 1 0 R R
goputright2 1 0 goputright1 2 0 R R
goputright2 2 0 goputright2 2 0 R R

goputright1 0 0 putright 1 0 R R
goputright2 0 0 putright 2 0 R R

putright 0 0 goswapleft 3 0 L L

goswapleft 1 0 goswapleft1 1 0 L L
goswapleft 2 0 goswapleft2 2 0 L L

goswapleft1 1 0 goswapleft1 1 0 L L
goswapleft1 2 0 goswapleft2 2 0 L L
goswapleft2 1 0 goswapleft1 1 0 L L
goswapleft2 2 0 goswapleft2 2 0 L L

goswapleft1 3 0 swapleft 1 0 R R
goswapleft2 3 0 swapleft 2 0 R R

swapleft 1 0 goswapright 3 0 R R
swapleft 2 0 goswapright 3 0 R R

goswapright 1 0 goswapright1 1 0 R R
goswapright 2 0 goswapright2 2 0 R R
goswapright 3 0 reject 3 0 S S

goswapright1 1 0 goswapright1 1 0 R R
goswapright1 2 0 goswapright2 2 0 R R
goswapright2 1 0 goswapright1 1 0 R R
goswapright2 2 0 goswapright2 2 0 R R

goswapright1 3 0 swapright 1 0 L L
goswapright2 3 0 swapright 2 0 L L

swapright 1 0 goswapleft 3 0 L L
swapright 2 0 goswapleft 3 0 L L

goswapleft 3 0 gocheckright 3 0 R R

gocheckright 0 0 checkright 0 0 L L
gocheckright 1 0 gocheckright 1 0 R R
gocheckright 2 0 gocheckright 2 0 R R
gocheckright 3 0 gocheckright 3 0 R R

checkright 1 0 gocheckleft1 0 0 L L
checkright 2 0 gocheckleft2 0 0 L L
checkright 3 0 accept 3 0 S S

gocheckleft1 1 0 gocheckleft1 1 0 L L
gocheckleft1 2 0 gocheckleft1 2 0 L L
gocheckleft1 3 0 checkleft1 3 0 L L
gocheckleft2 1 0 gocheckleft2 1 0 L L
gocheckleft2 2 0 gocheckleft2 2 0 L L
gocheckleft2 3 0 checkleft2 3 0 L L

checkleft1 1 0 gocheckright 3 0 R R
checkleft1 2 0 reject 2 0 S S
checkleft1 3 0 checkleft1 3 0 L L
checkleft2 1 0 reject 1 0 S S
checkleft2 2 0 gocheckright 3 0 R R
checkleft2 3 0 checkleft2 3 0 L L
