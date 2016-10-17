package xbony2.asmd2d;

import net.minecraft.item.crafting.RecipesCrafting;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ASMD2DClassTransformer implements IClassTransformer {

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass){
		boolean isObfuscated = !name.equals(transformedName);
		return name.equals("net.minecraft.item.crafting.RecipesCrafting") ? transform(basicClass, isObfuscated) : basicClass;
	}

	public byte[] transform(byte[] classBeingTransformed, boolean isObfuscated){
		try{
			ClassNode node = new ClassNode();
			ClassReader reader = new ClassReader(classBeingTransformed);
			reader.accept(node, 0);

			final String METHOD = isObfuscated ? "a" : "addRecipes";
			final String DESC = isObfuscated ? "(Lafe;)V" : "(Lnet/minecraft/item/crafting/CraftingManager;)V";

			for(MethodNode method : node.methods){
				if(method.name.equals(METHOD) && method.desc.equals(DESC)){
					System.out.println("[ASMD2D] Transforming RecipesCrafting class.");
					for(AbstractInsnNode instruction : method.instructions.toArray()){
						if(instruction.getOpcode() == Opcodes.RETURN)
							method.instructions.remove(instruction); //Removes the final return
						System.out.println(instruction.getClass() + " " + instruction.getOpcode());
					}

					InsnList newInstructions = new InsnList();
					
					newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
					newInstructions.add(new TypeInsnNode(Opcodes.NEW, "net/minecraft/item/ItemStack")); //#2
					newInstructions.add(new InsnNode(Opcodes.DUP));
					newInstructions.add(new FieldInsnNode(Opcodes.GETSTATIC, "net/minecraft/init/Items", "DIAMOND", "<Lnet/minecraft/item/Item;>"));
					newInstructions.add(new InsnNode(Opcodes.ICONST_1));
					newInstructions.add(new InsnNode(Opcodes.ICONST_0));
					newInstructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "net/minecraft/item/ItemStack", "<init>", "(Lnet/minecraft/block/Block;II)V"));
					newInstructions.add(new InsnNode(Opcodes.ICONST_5));
					newInstructions.add(new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object")); //#5
					newInstructions.add(new InsnNode(Opcodes.DUP));
					newInstructions.add(new InsnNode(Opcodes.ICONST_0));
					newInstructions.add(new LdcInsnNode("###")); //#6
					newInstructions.add(new InsnNode(Opcodes.AASTORE));
					newInstructions.add(new InsnNode(Opcodes.DUP));
					newInstructions.add(new InsnNode(Opcodes.ICONST_1));
					newInstructions.add(new LdcInsnNode("###")); //#6
					newInstructions.add(new InsnNode(Opcodes.AASTORE));
					newInstructions.add(new InsnNode(Opcodes.DUP));
					newInstructions.add(new InsnNode(Opcodes.ICONST_2));
					newInstructions.add(new LdcInsnNode("###")); //#6
					newInstructions.add(new InsnNode(Opcodes.AASTORE));
					newInstructions.add(new InsnNode(Opcodes.DUP));
					newInstructions.add(new InsnNode(Opcodes.ICONST_3));
					newInstructions.add(new IntInsnNode(Opcodes.BIPUSH, 35));
					newInstructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "<(C)Ljava/lang/Character;>")); //#8
					newInstructions.add(new InsnNode(Opcodes.AASTORE));
					newInstructions.add(new InsnNode(Opcodes.DUP));
					newInstructions.add(new InsnNode(Opcodes.ICONST_4));
					newInstructions.add(new FieldInsnNode(Opcodes.GETSTATIC, "net/minecraft/init/Blocks", "DIRT", "<Lnet/minecraft/item/Block;>"));
					newInstructions.add(new InsnNode(Opcodes.AASTORE));
					newInstructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/item/crafting/CraftingManager", "addRecipe", "<(Lnet/minecraft/item/ItemStack;[Ljava/lang/Object;)Lnet/minecraft/item/crafting/ShapelessRecipes;>")); //#10
					newInstructions.add(new InsnNode(Opcodes.POP));
					newInstructions.add(new InsnNode(Opcodes.RETURN));

					method.instructions.insert(newInstructions);
					break;
				}
			}

			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			node.accept(writer);
			return writer.toByteArray();
		}catch (Exception e){
			e.printStackTrace();
		}
		return classBeingTransformed;
	}
}
