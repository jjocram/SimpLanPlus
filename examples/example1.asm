addi $sp $sp -1
li $t1 0
sw $t1 0($hp)
push $a0
li $a0 1
push $a0
mv $al $fp
addi $a0 $al -1
lw $a0 0($a0)
lw $t1 0($sp)
pop
sw $t1 0($a0)
li $t1 0
sw $t1 0($hp)
push $a0
mv $al $fp
addi $a0 $al 0
lw $t1 0($sp)
pop
sw $t1 0($a0)
mv $al $fp
lw $a0 -1($al)
push $a0
mv $al $fp
addi $a0 $al 0
lw $a0 0($a0)
lw $t1 0($sp)
pop
sw $t1 0($a0)
mv $al $fp
lw $a0 0($al)
lw $a0 0($a0)
lw $a0 0($a0)
print $a0
halt
