<template>
  <model-viewer :src="modelUrl" camera-controls class="fit"/>
</template>

<script>
export default {
  name: 'ArtivactModelViewer',
  props: ['modelUrl']
}
</script>

<style scoped>

</style>
